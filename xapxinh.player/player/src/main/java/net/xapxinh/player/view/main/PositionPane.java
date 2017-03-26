/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Caprica Software Limited.
 */

package net.xapxinh.player.view.main;

import static net.xapxinh.player.Application.application;
import static net.xapxinh.player.util.TimeUtil.formatTime;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import net.xapxinh.player.event.TickEvent;
import net.xapxinh.player.view.StandardLabel;
import uk.co.caprica.vlcj.player.MediaPlayer;

import com.google.common.eventbus.Subscribe;

@SuppressWarnings("serial")
final class PositionPane extends JPanel {

    private final JLabel timeLabel;

    private final JSlider positionSlider;

    private final JLabel durationLabel;

    private long time;

    private final MediaPlayer mediaPlayer;

    private final AtomicBoolean sliderChanging = new AtomicBoolean();

    private final AtomicBoolean positionChanging = new AtomicBoolean();

    PositionPane(final MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;

        timeLabel = new StandardLabel("9:99:99");

        UIManager.put("Slider.paintValue", false);
        positionSlider = new JSlider();
        positionSlider.setMinimum(0);
        positionSlider.setMaximum(1000);
        positionSlider.setValue(0);

        positionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!positionChanging.get()) {
                    JSlider source = (JSlider) e.getSource();
                    if (source.getValueIsAdjusting()) {
                        sliderChanging.set(true);
                    }
                    else {
                        sliderChanging.set(false);
                    }
                    mediaPlayer.setPosition(source.getValue() / 1000.0f);
                }
            }
        });

        durationLabel = new StandardLabel("9:99:99");

        setLayout(new MigLayout("fill, insets 0 0 0 0", "[][grow][]", "[]"));

        add(timeLabel, "shrink");
        add(positionSlider, "grow");
        add(durationLabel, "shrink");

        timeLabel.setText("-:--:--");
        durationLabel.setText("-:--:--");

        application().subscribe(this);
    }

    private void refresh() {
        timeLabel.setText(formatTime(time));

        if (!sliderChanging.get()) {
            int value = (int) (mediaPlayer.getPosition() * 1000.0f);
            positionChanging.set(true);
            positionSlider.setValue(value);
            positionChanging.set(false);
        }
    }

    void setTime(long time) {
        this.time = time;
    }

    void setDuration(long duration) {
        durationLabel.setText(formatTime(duration));
    }

    @Subscribe
    public void onTick(TickEvent tick) {
        refresh();
    }
}